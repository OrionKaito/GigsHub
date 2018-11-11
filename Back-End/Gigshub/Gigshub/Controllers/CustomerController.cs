using AutoMapper;
using Gigshub.Model.Model;
using Gigshub.Service;
using Gigshub.ViewModel;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.Owin;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace Gigshub.Controllers
{
    //[Authorize]
    public class CustomerController : ApiController
    {
        private readonly ICustomerService _customerService;
        private ApplicationUserManager _userManager;
        private readonly string DIRECTORY_PATH = "/Images/Customer/";

        public ApplicationUserManager UserManager
        {
            get
            {
                return _userManager ?? Request.GetOwinContext().GetUserManager<ApplicationUserManager>();
            }
            private set
            {
                _userManager = value;
            }
        }

        public CustomerController(ICustomerService _customerService)
        {
            this._customerService = _customerService;
        }

        public CustomerController(ApplicationUserManager userManager)
        {
            UserManager = userManager;
        }

        [HttpGet]
        [Route("api/customer/get", Name = "GetCustomer")]
        public IHttpActionResult Get()
        {
            var result = new CustomerViewModel();
            //begin get data
            var name = User.Identity.Name;
            var cusInDb = _customerService.GetByName(name);

            if (cusInDb == null)
            {
                return NotFound(); //status code 404
            }

            Mapper.Map(cusInDb, result);

            List<CustomerViewModel> haiz = new List<CustomerViewModel>();
            haiz.Add(result);

            var data = new DataCustomerViewModel
            {
                Data = haiz,
            };
            //end get data
            return Ok(data); //sta    tus code 200
        }

        [HttpGet]
        [Route("api/customer/getbyid", Name = "GetCustomerById")]
        public IHttpActionResult GetById(long Id)
        {
            var result = new CustomerViewModel();
            //begin get data
            var cusInDb = _customerService.GetByID(Id);

            if(cusInDb == null)
            {
                return BadRequest("Customer not exist!"); //status code 400
            }

            Mapper.Map(cusInDb, result);

            List<CustomerViewModel> haiz = new List<CustomerViewModel>();
            haiz.Add(result);

            var data = new DataCustomerViewModel{
                Data = haiz,
            };
            //end get data
            return Ok(data); //status code 200
        }

        [HttpGet]
        [Route("api/customer/getbyname", Name = "GetCustomerByName")]
        public IHttpActionResult GetByName(string name)
        {
            var result = new CustomerViewModel();
            //begin get data
            var cusInDb = _customerService.GetByName(name);

            if (cusInDb == null)
            {
                return BadRequest("Customer not exist!"); //status code 400
            }

            Mapper.Map(cusInDb, result);

            List<CustomerViewModel> haiz = new List<CustomerViewModel>();
            haiz.Add(result);

            var data = new DataCustomerViewModel
            {
                Data = haiz,
            };
            //end get data
            return Ok(data); //status code 200
        }

        [HttpGet]
        [Route("api/customer/searchlikefullname", Name = "SearchLikeFullname")]
        public IHttpActionResult SearchLikeFullname(string fullname)
        {
            //begin get data
            var cusInDb = _customerService.SearchLikeFullName(fullname).Select(k => new CustomerViewModel {
                Id = k.Id,
                Address = k.Address,
                DateOfBirth = k.DateOfBirth,
                Email = k.Email,
                Fullname = k.Fullname,
                Gender = k.Gender,
                ImgPath = k.ImgPath,
                IsVerified = k.IsVerified,
                Phonenumber = k.Phonenumber,
                UserName = k.UserName,
            });

            if (cusInDb == null)
            {
                return BadRequest("There are no matching customer!"); //status code 400
            }

            var data = new DataCustomerViewModel
            {
                Data = cusInDb,
            };
            //end get data
            return Ok(data); //status code 200
        }

        [HttpPost]
        [Route("api/customer/create",  Name = "CreateCustomer")]
        public IHttpActionResult Create(CustomerCreateModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState); //status code 400
            }

            if(_customerService.GetByName(model.UserName) != null)
            {
                return BadRequest("Username is exist");
            }
            var customer = new Customer();
            //begin create data
            Mapper.Map(model, customer);
            customer.CreateDate = DateTime.Now;
            try
            {
                _customerService.Create(customer);
                _customerService.Save();
            }
            catch (Exception)
            {

                return BadRequest("Can't create customer"); //stastus code 400
            }
            //end create data
            return CreatedAtRoute("GetCustomerById", new { customer.Id }, customer); //status code 201
        }

        [HttpPost]
        [Route("api/customer/update", Name = "UpdateCustomer")]
        public IHttpActionResult Update()
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState); //status code 400
            }

            var name = User.Identity.Name;
            var custInDb = _customerService.GetByName(name);

            if (custInDb == null)
            {
                return NotFound(); //status code 404
            }

            //begin update data
            var httpRequest = HttpContext.Current.Request;
            var model = new CustomerUpdateModel
            {
                Id = custInDb.Id,
                //Address = httpRequest["Address"],
                //DateOfBirth = Convert.ToDateTime(httpRequest["Datetime"]),
                Fullname = httpRequest["Fullname"],
                //Gender = httpRequest["Gender"],
                //Phonenumber = httpRequest["Phonenumber"],
            };

            try
            {
                Mapper.Map(model, custInDb);
                _customerService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Failed to update Customer"); //status code 400
            }

            //delete image
            if (httpRequest.Files.Count > 0)
            {
                custInDb.ImgPath = "";
            }

            //upload imagge
            if (httpRequest.Files.Count > 0)
            {
                foreach (string file in httpRequest.Files)
                {
                    var postedFile = httpRequest.Files[file];
                    //create customer name
                    string imageName = new string(Path.GetFileNameWithoutExtension(postedFile.FileName)
                        .Take(10).ToArray()).Replace(" ", "-");
                    imageName = imageName + DateTime.Now.ToString("yy-mm-ss-ff") + Path.GetExtension(postedFile.FileName);
                    var relativePath = DIRECTORY_PATH + imageName;
                    var filePath = HttpContext.Current.Server.MapPath(relativePath);
                    postedFile.SaveAs(filePath);
                    //save to db
                    try
                    {
                        custInDb.ImgPath = relativePath;
                        _customerService.Save();
                    }
                    catch (Exception)
                    {
                        return BadRequest("Failed to update customer's image"); //status code 400
                    }
                }
            }
            //end update data
            return Ok("Success"); //status code 200
        }

        //[Authorize(Roles = "Admin")]
        [HttpPost]
        [Route("api/customer/delete", Name = "DeleteCustomer")]
        public IHttpActionResult Delete(long Id)
        {
            var CusInDb = _customerService.GetByID(Id);

            if (CusInDb == null)
            {
                return BadRequest("Customer is not exist!"); //status code 400
            }
            //begin delete data
            try
            {
                CusInDb.IsDeleted = true;
                _customerService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Can't delete customer"); //statis code 400
            }
            //end delete data
            return Ok("Success"); //status code 200
        }

        //[Authorize(Roles = "Admin")]
        [HttpPost]
        [Route("api/customer/verify", Name = "VerifyCustomer")]
        public IHttpActionResult Verify(long Id)
        {
            var CusInDb = _customerService.GetByID(Id);

            if (CusInDb == null)
            {
                return BadRequest("Customer is not exist!"); //status code 400
            }
            //begin update data
            try
            {
                CusInDb.IsVerified = true;
                _customerService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Can't verify customer"); //statis code 400
            }
            //end update data
            return Ok("Success"); //status code 200
        }

        [HttpGet]
        [Route("api/customer/checkverify", Name = "CheckVerify")]
        public IHttpActionResult CheckVerify(long Id)
        {
            var CusInDb = _customerService.GetByID(Id);

            if (CusInDb == null)
            {
                return BadRequest("Customer is not exist!"); //status code 400
            }
            return Ok(CusInDb.IsVerified); //status code 200
        }

        [AllowAnonymous]
        [HttpPost]
        [Route("api/customer/confirmemail", Name = "ConfirmEmail")]
        public IHttpActionResult ConfirmEmail(ConfirmEmailModel model)
        {
            var user = UserManager.FindByName(model.name);

            if (user == null)
            {
                return BadRequest("User is not exist"); //status code 400
            }

            var cusInDb = _customerService.GetByName(model.name);

            if (cusInDb.EmailConfirmCode == model.Code)
            {
                cusInDb.EmailConfirm = true;
                _customerService.Save();
                user.EmailConfirmed = true;
                UserManager.Update(user);
            }

            else
            {
                return BadRequest("Wrong code! please try again"); //status code 400
            }

            return Ok("Success"); //status code 200
        }
    }
}
