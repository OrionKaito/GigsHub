using Gigshub.Model.Model;
using Gigshub.Service;
using System;
using System.Web.Http;

namespace Gigshub.Controllers
{
    public class AttendanceController : ApiController
    {
        private readonly IAttendanceService _attendanceService;
        private readonly ICustomerService _customerService;
        private readonly IEventService _eventService;

        public AttendanceController(IAttendanceService _attendanceService,
            ICustomerService _customerService,
            IEventService _eventService)
        {
            this._attendanceService = _attendanceService;
            this._customerService = _customerService;
            this._eventService = _eventService;
        }

        [HttpPost]
        [Route("api/attendance/attend", Name = "AttendEvent")]
        public IHttpActionResult Attend(long eventId)
        {
            //var name = User.Identity.Name;
            var cusInDb = _customerService.GetByName("admin");

            if (cusInDb == null)
            {
                return NotFound(); //status code 404
            }

            if (_attendanceService.Get(cusInDb.Id, eventId) != null)
            {
                return BadRequest("You already attend this event"); //status code 400
            }

            if (_eventService.GetByID(eventId).IsDeleted == true)
            {
                return NotFound(); //status code 404
            }

            var attendance = new Attendance
            {
                EventId = eventId,
                CustomerId = cusInDb.Id
            };

            try
            {
                _attendanceService.Create(attendance);
                _attendanceService.Save();
            }
            catch (System.Exception)
            {
                return BadRequest("Can not create attend"); //stastus code 400
            }

            return Ok("Success"); //status code 200
        }

        [HttpPost]
        [Route("api/attendance/unattend", Name = "UnAttendEvent")]
        public IHttpActionResult UnAttend(long followeeId)
        {
            //var name = User.Identity.Name;
            var cusInDb = _customerService.GetByName("admin");

            if (cusInDb == null)
            {
                return NotFound(); //status code 404
            }

            var attend = _attendanceService.Get(cusInDb.Id, followeeId);

            if (attend == null)
            {
                return BadRequest("You not attend this event yet"); //status code 400
            }
            //begin delete data
            try
            {
                _attendanceService.Delete(attend.Id);
                _attendanceService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Can not unattend"); //stastus code 400
            }
            //end delete data
            return Ok("Success"); //status code 200
        }
    }
}
