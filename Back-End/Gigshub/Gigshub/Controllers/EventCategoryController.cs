using Gigshub.Model.Model;
using Gigshub.Service;
using System;
using System.Web.Http;

namespace Gigshub.Controllers
{
    public class EventCategoryController : ApiController
    {
        private readonly IEventCategoryService _eventCategoryService;

        public EventCategoryController(IEventCategoryService _eventCategoryService)
        {
            this._eventCategoryService = _eventCategoryService;
        }

        [HttpGet]
        [Route("api/eventcategory/getbyid", Name = "GetEventCategorytById")]
        public IHttpActionResult GetById(long Id)
        {
            //begin get data
            var category = _eventCategoryService.GetById(Id).Name;

            if (category == null)
            {
                return BadRequest("Category not exist"); //status code 400
            }
            //end get data
            return Ok(category);
        }

        [HttpPost]
        [Route("api/eventcategory/create", Name = "CreateEventCategory")]
        public IHttpActionResult Create(string name)
        {
            if(name == null)
            {
                return BadRequest("Category's name can't not be null"); //status code 400
            }

            if (_eventCategoryService.GetByName(name) != null)
            {
                return BadRequest("Category is exist"); //stastus code 400
            }

            var category = new EventCategory {
                Name = name
            };
            //begin create data
            try
            {
                _eventCategoryService.Create(category);
                _eventCategoryService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Can't creat category"); //stastus code 400
            }
            //end create data
            return CreatedAtRoute("GetEventCategorytById", new { category.Id }, category); //status code 201
        }

        [HttpPost]
        [Route("api/eventcategory/delete", Name = "DeleteEventCategory")]
        public IHttpActionResult Delete(long Id)
        {
            var categoryInDb = _eventCategoryService.GetById(Id);

            if (categoryInDb == null)
            {
                return BadRequest("Category is not exist!"); //status code 400
            }
            //begin delete data
            try
            {
                categoryInDb.IsDelete = true;
                _eventCategoryService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Can't delete event's category"); //status code 400
            }
            //end delete data
            return Ok("Success"); //status code 200
        }
    }
}
