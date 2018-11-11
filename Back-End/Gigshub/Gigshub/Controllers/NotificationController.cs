using Gigshub.Model.Model;
using Gigshub.Service;
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
            ICollection<Notification> listData = new Collection<Notification>();

            foreach (var item in userNotifcations)
            {
                var data = _notificationService.GetById(item.Id);
                listData.Add(data);
            }
            //end get data
            return Ok(listData); //status code 200
        }
    }
}
