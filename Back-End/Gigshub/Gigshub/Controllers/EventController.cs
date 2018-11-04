using AutoMapper;
using Gigshub.Model.Model;
using Gigshub.Service;
using Gigshub.ViewModel;
using System;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace Gigshub.Controllers
{
    //[Authorize]
    public class EventController : ApiController
    {
        private readonly IEventService _eventService;
        private readonly IEventImageService _eventImageSerivce;
        private readonly ICustomerService _customerService;
        private readonly IEventCategoryService _eventCategoryService;
        private readonly string DIRECTORY_PATH = "/Images/Event/";
        
        public EventController(IEventService _eventService,
            IEventImageService _eventImageSerivce,
            ICustomerService _customerService,
            IEventCategoryService _eventCategoryService)
        {
            this._eventService = _eventService;
            this._eventImageSerivce = _eventImageSerivce;
            this._customerService = _customerService;
            this._eventCategoryService = _eventCategoryService;
        }

        [HttpGet]
        [Route("api/event/getall", Name = "GetAllEvent")]
        public IHttpActionResult GetAll()
        {
            //begin get data
            try
            {
                var result = _eventService.GetAll().Select(k => new EventViewModel
                {
                    Id = k.Id,
                    Title = k.Title,
                    City = k.City,
                    Address = k.Address,
                    Description = k.Description,
                    Artist = k.Artist,
                    NumberOfAttender = k.NumberOfAttender,
                    Rating = k.Rating,
                    IsDeleted = k.IsDeleted,
                    IsSale = k.IsSale,
                    Price = k.Price,
                    OwnerName = _customerService.GetByID(k.OwnerID).UserName,
                    DateTime = k.DateTime.ToString("D"),
                    Category = _eventCategoryService.GetById(k.CategoryID).Name,
                    ImgPath = _eventImageSerivce.GetOneByEventId(k.Id)
                });

                return Ok(result);
            }
            catch (Exception)
            {

                return BadRequest("Failed to get data"); //status code 400
            }
            //end get data
        }

        [HttpGet]
        [Route("api/event/getuserevent", Name = "GetUserEvent")]
        public IHttpActionResult GetUserEvent()
        {
            var name = User.Identity.Name;
            var Id = _customerService.GetByName(name).Id;
            //begin get data
            try
            {
                var result = _eventService.GetAllUserEvent(Id).Select(k => new EventViewModel
                {
                    Id = k.Id,
                    Title = k.Title,
                    City = k.City,
                    Address = k.Address,
                    Description = k.Description,
                    Artist = k.Artist,
                    NumberOfAttender = k.NumberOfAttender,
                    Rating = k.Rating,
                    IsDeleted = k.IsDeleted,
                    IsSale = k.IsSale,
                    Price = k.Price,
                    OwnerName = _customerService.GetByID(k.OwnerID).UserName,
                    DateTime = k.DateTime.ToString("D"),
                    Category = _eventCategoryService.GetById(k.CategoryID).Name,
                    ImgPath = _eventImageSerivce.GetOneByEventId(k.Id)
                });

                return Ok(result);
            }
            catch (Exception)
            {

                return BadRequest("Failed to get data"); //status code 400
            }
            //end get data
        }

        [HttpGet]
        [Route("api/event/getattendingevent", Name = "GetAttendingEvent")]
        public IHttpActionResult GetAttendingEvent(long Id)
        {
            //begin get data
            return Ok("Coming soon!");
            //end get data
        }

        [HttpGet]
        [Route("api/event/getbyid", Name = "GetEventById")]
        public IHttpActionResult GetById(long Id)
        {
            //begin get data
            var Event = _eventService.GetByID(Id);
            if (Event == null)
            {
                return BadRequest("Event not exist"); //status code 400
            }

            var name = _customerService.GetByID(Event.OwnerID).UserName;

            var result = new EventViewModel {
                Id = Event.Id,
                Title = Event.Title,
                City = Event.City,
                Address = Event.Address,
                Description = Event.Description,
                Artist = Event.Artist,
                NumberOfAttender = Event.NumberOfAttender,
                Rating = Event.Rating,
                IsDeleted = Event.IsDeleted,
                IsSale = Event.IsSale,
                Price = Event.Price,
                OwnerName = name,
                DateTime = Event.DateTime.ToString("D"),
                Category = "AAA",
                ImgPath = _eventImageSerivce.GetOneByEventId(Event.Id)
            };
            //end get data
            return Ok(result);
        }

        [HttpPost]
        [Route("api/event/create", Name = "CreateEvent")]
        public IHttpActionResult Create()
        {
            //begin create data
            var httpRequest = HttpContext.Current.Request;
            var model = new EventCreateModel
            {
                Title = httpRequest["Title"],
                City = httpRequest["City"],
                Address = httpRequest["Address"],
                Description = httpRequest["Description"],
                Artist = httpRequest["Artist"],
                DateTime = Convert.ToDateTime(httpRequest["DateTime"]),
                IsSale = Convert.ToBoolean(httpRequest["IsSale"]),
                Price = Convert.ToDouble(httpRequest["Price"]),
                CategoryID = Convert.ToInt64(httpRequest["Category"]),
            };

            var name = User.Identity.Name;
            long OwnerID = _customerService.GetByName(name).Id;
            
            var Event = new Event();
            Mapper.Map(model, Event);

            Event.OwnerID = OwnerID;
            Event.CreateDate = DateTime.Now;

            try
            {
                _eventService.Create(Event);
                _eventService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Failed to create Event"); //status code 400
            }

            //begin upload image

            if (httpRequest.Files.Count > 0)
            {
                foreach (string file in httpRequest.Files)
                {
                    var postedFile = httpRequest.Files[file];
                    //create customer filename
                    string imageName = new string(Path.GetFileNameWithoutExtension(postedFile.FileName)
                        .Take(10)
                        .ToArray())
                        .Replace(" ", "-");

                    imageName = imageName + "-" + DateTime.Now.ToString("yyyy-dd-M--HH-mm-ss") + Path.GetExtension(postedFile.FileName);

                    //create directory if not exist
                    var abDirectoryPath = HttpContext.Current.Server.MapPath(DIRECTORY_PATH);
                    if (!Directory.Exists(abDirectoryPath))
                    {
                        Directory.CreateDirectory(abDirectoryPath);
                    }

                    var relativePath = Path.Combine(DIRECTORY_PATH, imageName);
                    var filePath = HttpContext.Current.Server.MapPath(relativePath);

                    postedFile.SaveAs(filePath);
                    long fileSize = new FileInfo(filePath).Length; ;
                    //Save to db
                    EventImage image = new EventImage
                    {
                        ImagePath = relativePath,
                        EventId = Event.Id,
                    };

                    try
                    {
                        _eventImageSerivce.Create(image);
                        _eventImageSerivce.Save();
                    }
                    catch (Exception)
                    {
                        return BadRequest("Failed to create event's image"); //status code 400
                    }
                }
            }
            //end upload image
            //end create data
            return Ok("Success"); //status code 200
        }

        [HttpPost]
        [Route("api/event/update", Name = "UpdateEvent")]
        public IHttpActionResult Update()
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState); //status code 400
            }

            //begin update data
            var httpRequest = HttpContext.Current.Request;
            var model = new EventUpdateModel
            {
                Id = Convert.ToInt64(httpRequest["Id"]),
                DateTime = Convert.ToDateTime(httpRequest["Datetime"]),
                Description = httpRequest["Description"],
                City = httpRequest["City"],
                Address = httpRequest["Address"],
                Artist = httpRequest["Artist"],
                IsSale = Convert.ToBoolean(httpRequest["IsSale"]),
                Price = Convert.ToDouble(httpRequest["Price"]),
                CategoryID = Convert.ToInt32(httpRequest["Category"]),
                
            };

            var EventInDb = _eventService.GetByID(model.Id);
            if (EventInDb == null)
            {
                return NotFound(); //status code 404
            }

            //check authorize
            var name = User.Identity.Name;
            var custInDb = _customerService.GetByID(EventInDb.OwnerID).UserName;
            if (name != custInDb)
            {
                return BadRequest("Customer is not authorized to see or edit this record");
            }

            try
            {
                Mapper.Map(model, EventInDb);
                _eventService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Failed to update Event"); //status code 400
            }

            //delete image 
            if (httpRequest.Files.Count > 0)
            {
                _eventImageSerivce.DeleteByEventId(model.Id);
            }

            //upload image
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
                    EventImage image = new EventImage()
                    {
                        ImagePath = relativePath,
                        EventId = model.Id,
                    };

                    try
                    {
                        _eventImageSerivce.Create(image);
                        _eventImageSerivce.Save();
                    }
                    catch (Exception)
                    {
                        return BadRequest("Failed to update event's image"); //status code 400
                    }
                }
            }
            //end update data
            return Ok("Success"); //status code 200
        }

        [HttpPost]
        [Route("api/event/delete", Name = "DeleteEvent")]
        public IHttpActionResult Delete(long Id)
        {
            var EventInDb = _eventService.GetByID(Id);

            //check authorize
            var name = User.Identity.Name;
            var custInDb = _customerService.GetByID(EventInDb.OwnerID).UserName;
            if (name != custInDb)
            {
                return BadRequest("Customer is not authorized to see or edit this record");
            }

            if (EventInDb == null)
            {
                return BadRequest("Event is not exist!"); //status code 400
            }
            //begin delete data
            try
            {
                _eventImageSerivce.DeleteByEventId(Id);
                EventInDb.IsDeleted = true;
                _eventService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Failed to delete event"); //status code 400
            }
            //end delete data
            return Ok("Success"); //status code 200
        }
    }
}
