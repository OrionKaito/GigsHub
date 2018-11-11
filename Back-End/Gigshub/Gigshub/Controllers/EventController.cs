using AutoMapper;
using Gigshub.Model.Model;
using Gigshub.Service;
using Gigshub.ViewModel;
using System;
using System.Collections.Generic;
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
        private readonly IAttendanceService _attendanceService;
        private readonly INotificationService _notificationService;
        private readonly IUserNotificationService _userNotificationService;
        private readonly IFollowingService _followingService;
        private readonly string DIRECTORY_PATH = "/Images/Event/";
        
        public EventController(IEventService _eventService,
            IEventImageService _eventImageSerivce,
            ICustomerService _customerService,
            IEventCategoryService _eventCategoryService,
            IAttendanceService _attendanceService,
            INotificationService _notificationService,
            IUserNotificationService _userNotificationService,
            IFollowingService _followingService)
        {
            this._eventService = _eventService;
            this._eventImageSerivce = _eventImageSerivce;
            this._customerService = _customerService;
            this._eventCategoryService = _eventCategoryService;
            this._attendanceService = _attendanceService;
            this._notificationService = _notificationService;
            this._userNotificationService = _userNotificationService;
            this._followingService = _followingService;
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
                    OwnerName = k.Owner.UserName,
                    OwnderFullname = k.Owner.Fullname,
                    ICusVerified = k.Owner.IsVerified,
                    Date = k.DateTime.ToString("D"),
                    Time = k.DateTime.ToString("t"),
                    Category = k.Category.Name,
                    ImgPath = _eventImageSerivce.GetOneByEventId(k.Id)
                });

                if (!result.Any())
                {
                    return Ok("There is not any event yet!"); // status code 200
                }

                var data = new DataEventViewModel
                {
                    Data = result,
                };

                return Ok(data);
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
                    OwnerName = k.Owner.UserName,
                    OwnderFullname = k.Owner.Fullname,
                    ICusVerified = k.Owner.IsVerified,
                    Date = k.DateTime.ToString("D"),
                    Time = k.DateTime.ToString("t"),
                    Category = k.Category.Name,
                    ImgPath = _eventImageSerivce.GetOneByEventId(k.Id)
                });

                if (!result.Any())
                {
                    return Ok("There is not any event yet!"); // status code 200
                }

                var data = new DataEventViewModel
                {
                    Data = result,
                };

                return Ok(data);
            }
            catch (Exception)
            {

                return BadRequest("Failed to get data"); //status code 400
            }
            //end get data
        }

        [HttpGet]
        [Route("api/event/getattendingevent", Name = "GetAttendingEvent")]
        public IHttpActionResult GetAttendingEvent()
        {
            var name = User.Identity.Name;
            var Id = _customerService.GetByName(name).Id;
            //begin get data
            try
            {
                var result = _attendanceService.GetByCusId(Id).Select(k => new EventViewModel {
                    Id = k.Event.Id,
                    Title = k.Event.Title,
                    City = k.Event.City,
                    Address = k.Event.Address,
                    Description = k.Event.Description,
                    Artist = k.Event.Artist,
                    NumberOfAttender = k.Event.NumberOfAttender,
                    Rating = k.Event.Rating,
                    IsDeleted = k.Event.IsDeleted,
                    IsSale = k.Event.IsSale,
                    Price = k.Event.Price,
                    OwnerName = k.Event.Owner.UserName,
                    OwnderFullname = k.Event.Owner.Fullname,
                    ICusVerified = k.Event.Owner.IsVerified,
                    Date = k.Event.DateTime.ToString("D"),
                    Time = k.Event.DateTime.ToString("t"),
                    Category = k.Event.Category.Name,
                    ImgPath = _eventImageSerivce.GetOneByEventId(k.Event.Id)
                });

                if (!result.Any())
                {
                    return Ok("There is not any event yet!"); // status code 200
                }

                var data = new DataEventViewModel
                {
                    Data = result,
                };

                return Ok(data);

            }
            catch (Exception)
            {
                return NotFound(); //status code 404
            }
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
                OwnerName = Event.Owner.UserName,
                OwnderFullname = Event.Owner.Fullname,
                ICusVerified = Event.Owner.IsVerified,
                Date = Event.DateTime.ToString("D"),
                Time = Event.DateTime.ToString("t"),
                Category = Event.Category.Name,
                ImgPath = _eventImageSerivce.GetOneByEventId(Event.Id)
            };
            //end get data

            List<EventViewModel> haiz = new List<EventViewModel>();
            haiz.Add(result);

            var data = new DataEventViewModel
            {
                Data = haiz,
            };

            return Ok(data);
        }

        [HttpGet]
        [Route("api/event/searchliketitle", Name = "SearchLikeTitle")]
        public IHttpActionResult SearchLikeTitle(string strSeach)
        {
            //begin get data
            try
            {
                var result = _eventService.SearchLikeTitle(strSeach).Select(k => new EventViewModel
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
                    OwnerName = k.Owner.UserName,
                    OwnderFullname = k.Owner.Fullname,
                    ICusVerified = k.Owner.IsVerified,
                    Date = k.DateTime.ToString("D"),
                    Time = k.DateTime.ToString("t"),
                    Category = k.Category.Name,
                    ImgPath = _eventImageSerivce.GetOneByEventId(k.Id)
                });

                if (!result.Any())
                {
                    return Ok("There are no matching event!"); // status code 200
                }

                var data = new DataEventViewModel
                {
                    Data = result,
                };

                return Ok(data);
            }
            catch (Exception)
            {
                return NotFound(); //satus code 404
            }
            //end get data
        }

        [HttpGet]
        [Route("api/event/searchlikecity", Name = "SearchLikeCity")]
        public IHttpActionResult SearchLikeCity(string strSeach)
        {
            //begin get data
            try
            {
                var result = _eventService.SearchLikeCity(strSeach).Select(k => new EventViewModel
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
                    OwnerName = k.Owner.UserName,
                    OwnderFullname = k.Owner.Fullname,
                    ICusVerified = k.Owner.IsVerified,
                    Date = k.DateTime.ToString("D"),
                    Time = k.DateTime.ToString("t"),
                    Category = k.Category.Name,
                    ImgPath = _eventImageSerivce.GetOneByEventId(k.Id)
                });

                if (!result.Any())
                {
                    return Ok("There are no matching event!"); // status code 200
                }

                var data = new DataEventViewModel
                {
                    Data = result,
                };

                return Ok(data);
            }
            catch (Exception)
            {
                return NotFound(); //satus code 404
            }
            //end get data
        }

        [HttpGet]
        [Route("api/event/searchbycategory", Name = "SearchByCategory")]
        public IHttpActionResult SearchByCategory(string strSeach)
        {
            //begin get data
            try
            {
                var result = _eventService.SearchByCategory(strSeach).Select(k => new EventViewModel
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
                    OwnerName = k.Owner.UserName,
                    OwnderFullname = k.Owner.Fullname,
                    ICusVerified = k.Owner.IsVerified,
                    Date = k.DateTime.ToString("D"),
                    Time = k.DateTime.ToString("t"),
                    Category = k.Category.Name,
                    ImgPath = _eventImageSerivce.GetOneByEventId(k.Id)
                });

                if (!result.Any())
                {
                    return Ok("There are no matching event!"); // status code 200
                }

                var data = new DataEventViewModel
                {
                    Data = result,
                };

                return Ok(data);
            }
            catch (Exception)
            {
                return NotFound(); //satus code 404
            }
            //end get data
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

            if (_eventService.GetByTitle(model.Title) != null)
            {
                return BadRequest("Title's event already exist");
            }

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
            //create notification
            Notification notification = new Notification
            {
                DateTime = DateTime.Now,
                Event = Event,
                Type = NotificationType.EventCreated,
            };

            try
            {
                _notificationService.Create(notification);
                _notificationService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Can't create notification"); //status code 400
            }

            var follower = _followingService.GetByFolloweeId(Event.OwnerID).Select(k => k.Follower);

            foreach (var customer in follower)
            {
                var userNotification = new UserNotifcation
                {
                    CustomerId = customer.Id,
                    NotificationId = notification.Id,
                };
                _userNotificationService.Create(userNotification);
            }
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

            //create notification
            Notification notification = new Notification
            {
                DateTime = DateTime.Now,
                Event = EventInDb,
                Type = NotificationType.EventUpdated,
            };

            _notificationService.Create(notification);
            _notificationService.Save();

            var attendees = _attendanceService.GetByEventId(EventInDb.Id).Select(k => k.Customer);

            foreach (var customer in attendees)
            {
                var userNotification = new UserNotifcation
                {
                    CustomerId = customer.Id,
                    NotificationId = notification.Id,
                };
                _userNotificationService.Create(userNotification);
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
                //_eventImageSerivce.DeleteByEventId(Id);
                EventInDb.IsDeleted = true;


                //create notification
                Notification notification = new Notification
                {
                    DateTime = DateTime.Now,
                    Event = EventInDb,
                    Type = NotificationType.EventCanceled,
                };

                _notificationService.Create(notification);
                _notificationService.Save();

                var attendees = _attendanceService.GetByEventId(EventInDb.Id).Select(k => k.Customer);

                foreach (var customer in attendees)
                {
                    var userNotification = new UserNotifcation
                    {
                        CustomerId = customer.Id,
                        NotificationId = notification.Id,
                    };
                    _userNotificationService.Create(userNotification);
                }

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
