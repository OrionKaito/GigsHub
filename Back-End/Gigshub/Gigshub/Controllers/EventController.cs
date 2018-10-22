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
        private readonly string DIRECTORY_PATH = "/Images/Event/";
        
        public EventController(IEventService _eventService,
            IEventImageService _eventImageSerivce,
            ICustomerService _customerService)
        {
            this._eventService = _eventService;
            this._eventImageSerivce = _eventImageSerivce;
            this._customerService = _customerService;
        }

        [HttpGet]
        [Route("api/event/getbyid", Name = "GetEventById")]
        public IHttpActionResult GetById(long Id)
        {
            var result = new EventViewModel();
            //begin get data
            var Event = _eventService.GetByID(Id);

            if (Event == null)
            {
                return BadRequest("Event not exist"); //status code 400
            }

            Mapper.Map(Event, result);

            var name = User.Identity.Name;
            result.OwnerName = name;
            //end get data
            return Ok(result);
        }

        [HttpPost]
        [Route("api/event/create", Name = "CreateEvent")]
        public IHttpActionResult Create()
        {
            //begin create data
            var httpRequest = HttpContext.Current.Request;
            var EventCreateModel = new EventCreateModel
            {
                Name = httpRequest["Name"],
                Title = httpRequest["Title"],
                Location = httpRequest["Location"],
                Description = httpRequest["Description"],
                DateTime = Convert.ToDateTime(httpRequest["DateTime"])
            };

            var name = User.Identity.Name;
            long OwnerID = _customerService.GetByName(name).Id;

            var Event = new Event();
            Mapper.Map(EventCreateModel, Event);

            Event.OwnerID = OwnerID;
            Event.CreateDate = DateTime.Now;

            try
            {
                _eventService.Create(Event);
                _eventService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Failed to create Event");
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
                    _eventImageSerivce.Create(image);
                    _eventImageSerivce.Save();
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
            var EventUpdateModel = new EventUpdateModel
            {
                Id = Convert.ToInt64(httpRequest["Id"]),
                DateTime = Convert.ToDateTime(httpRequest["Dateime"]),
                Description = httpRequest["Description"],
                Location = httpRequest["Location"],
                Name = httpRequest["Name"],
                Title = httpRequest["Title"],
            };

            var EventInDb = _eventService.GetByID(EventUpdateModel.Id);
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
                Mapper.Map(EventUpdateModel, EventInDb);
                _eventService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Failed to update Event");
            }



            //end update data
            return Ok("Success"); //status code 200
        }

        [HttpPost]
        [Route("api/event/delete", Name = "DeleteEvent")]
        public IHttpActionResult Delete(long Id)
        {
            var EventInDb = _eventService.GetByID(Id);

            if (EventInDb == null)
            {
                return BadRequest("Event is not exist!"); //status code 400
            }
            //begin delete data
            EventInDb.IsDelete = true;
            _eventService.Save();
            //end delete data
            return Ok("Success"); //status code 200
        }
    }
}
