using AutoMapper;
using Gigshub.Model.Model;
using Gigshub.Service;
using Gigshub.ViewModel;
using System;
using System.Linq;
using System.Web.Http;

namespace Gigshub.Controllers
{
    public class CommentController : ApiController
    {
        private readonly ICommentService _commentSerivce;
        private readonly IEventService _eventService;

        public CommentController(ICommentService _commentSerivce,
            IEventService _eventService)
        {
            this._commentSerivce = _commentSerivce;
            this._eventService = _eventService;
        }

        [HttpGet]
        [Route("api/comment/getbyeventid", Name = "GetByEventId")]
        public IHttpActionResult GetByEventId(long eventId)
        {
            var eventInDb = _eventService.GetByID(eventId);
            
            if(eventInDb == null)
            {
                return NotFound(); //status code 404
            }
            //begin get data
            try
            {
                var result = _commentSerivce.GetByEventId(eventId).Select(k => new CommentViewModel
                {
                    Fullname = k.Customer.Fullname,
                    ImgPath = k.Customer.ImgPath,
                    Content = k.Content,
                });

                if (!result.Any())
                {
                    return Ok("There is not any comment yet!"); // status code 200
                }

                var data = new DataCommentViewModel
                {
                    Data = result,
                };

                return Ok(data); //status code 200
            }
            catch (Exception)
            {
                return BadRequest("Failed to get data"); //status code 400
            }
            //end get data
        }

        [HttpPost]
        [Route("api/comment/create", Name = "CreateComment")]
        public IHttpActionResult Create(CommentCreateModel model)
        {
            var comment = new Comment();
            //begin create data
            Mapper.Map(model, comment);
            try
            {
                _commentSerivce.Create(comment);
                _commentSerivce.Save();
            }
            catch (Exception)
            {

                return BadRequest("Can't create comment"); //stastus code 400
            }
            //end create data
            return CreatedAtRoute("GetCommentId", new { comment.Id }, comment); //status code 201
        }
    }
}