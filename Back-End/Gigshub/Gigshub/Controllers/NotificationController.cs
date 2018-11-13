using Gigshub.Model.Model;
using Gigshub.Service;
using Gigshub.ViewModel;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Web.Http;

namespace Gigshub.Controllers
{
    public class NotificationController : ApiController
    {
        private readonly INotificationService _notificationService;
        private readonly IUserNotificationService _userNotificationService;
        private readonly ICustomerService _customerService;

        public NotificationController (INotificationService _notificationService,
            IUserNotificationService _userNotificationService,
            ICustomerService _customerService)
        {
            this._notificationService = _notificationService;
            this._userNotificationService = _userNotificationService;
            this._customerService = _customerService;
        }

        [HttpGet]
        [Route("api/notification/getnumberofnotification", Name = "GetNumberOfNotification")]
        public IHttpActionResult GetNumberOfNotification()
        {
            var name = User.Identity.Name;
            var cusInDb = _customerService.GetByName(name);

            if (cusInDb == null)
            {
                return NotFound(); //status code 404
            }

            //begin get data
            try
            {
                var result = _userNotificationService.GetNumberOfNotification(cusInDb.Id);
                return Ok(result); //status code 200
            }
            catch (Exception)
            {
                return BadRequest("Failed to get data"); //status code 400
            }
            //end get data
        }

        [HttpGet]
        [Route("api/notification/getbyuserid", Name = "GetByUserId")]
        public IHttpActionResult GetByUserId()
        {
            var name = User.Identity.Name;
            var cusInDb = _customerService.GetByName(name);

            if (cusInDb == null)
            {
                return NotFound(); //status code 404
            }

            //begin get data
            var userNotifcations = _userNotificationService.GetByUserId(cusInDb.Id);

            if (userNotifcations == null)
            {
                return Ok("There are not any notfication yet"); //status code 200
            }

            var data = new List<NotificationViewModel>();

            foreach (var item in userNotifcations)
            {
                if (item.Notification.Type == NotificationType.EventCreated)
                {
                    var result = new NotificationViewModel
                    {
                        Fullname = cusInDb.Fullname,
                        ImgPath = cusInDb.ImgPath,
                        EventId = item.Notification.Event.Id,
                        Content = "have Create a new event",
                    };
                    data.Add(result);
                }
                else if (item.Notification.Type == NotificationType.EventUpdated)
                {
                    var result = new NotificationViewModel
                    {
                        Fullname = cusInDb.Fullname,
                        ImgPath = cusInDb.ImgPath,
                        EventId = item.Notification.Event.Id,
                        Content = "have update their event",
                    };
                    data.Add(result);
                }
                else if (item.Notification.Type == NotificationType.EventCanceled)
                {
                    var result = new NotificationViewModel
                    {
                        Fullname = cusInDb.Fullname,
                        ImgPath = cusInDb.ImgPath,
                        EventId = item.Notification.Event.Id,
                        Content = "have cancel their event",
                    };
                    data.Add(result);
                }
                else
                {
                    return NotFound(); //status code 404
                }
            }
            //end get data
            return Ok(data); //status code 200
        }
    }
}
