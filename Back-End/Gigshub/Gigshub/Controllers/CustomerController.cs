using AutoMapper;
using Gigshub.Model.Model;
using Gigshub.Service;
using Gigshub.ViewModel;
using System;
using System.Web.Http;

namespace Gigshub.Controllers
{
    //[Authorize]
    public class CustomerController : ApiController
    {
        private readonly ICustomerService _customerService;

        public CustomerController(ICustomerService _customerService)
        {
            this._customerService = _customerService;
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
            //end get data
            return Ok(result); //status code 200
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
            //end get data
            return Ok(result); //status code 200
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
            //end get data
            return Ok(result); //status code 200
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
        public IHttpActionResult Update(CustomerUpdateModel model)
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
            Mapper.Map(model, custInDb);
            _customerService.Save();
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
    }
}
