using Gigshub.Model.Model;
using Gigshub.Service;
using System;
using System.Web.Http;

namespace Gigshub.Controllers
{
    public class FollowingController : ApiController
    {
        private readonly IFollowingService _followingService;
        private readonly ICustomerService _customerService;

        public FollowingController(IFollowingService _followingService,
            ICustomerService _customerService)
        {
            this._followingService = _followingService;
            this._customerService = _customerService;
        }

        [HttpPost]
        [Route("api/following/follow", Name = "FollowCustomer")]
        public IHttpActionResult Follow(long followeeId)
        {
            var name = User.Identity.Name;
            var cusInDb = _customerService.GetByName(name);

            if (cusInDb == null)
            {
                return NotFound(); //status code 404
            }

            if (_followingService.Get(cusInDb.Id, followeeId) != null)
            {
                return BadRequest("You already follow this person"); //status code 400
            }

            if (_customerService.GetByID(followeeId).IsDeleted == true)
            {
                return NotFound(); //status code 404
            }

            var following = new Following
            {
                FolloweeId = followeeId,
                FollowerId = cusInDb.Id
            };

            try
            {
                _followingService.Create(following);
                _followingService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Can not create follow"); //stastus code 400
            }
            return Ok("Success"); //status code 200
        }

        [HttpPost]
        [Route("api/following/unfollow", Name = "UnFollowCustomer")]
        public IHttpActionResult UnFollow(long followeeId)
        {
            var name = User.Identity.Name;
            var cusInDb = _customerService.GetByName(name);

            if (cusInDb == null)
            {
                return NotFound(); //status code 404
            }

            var following = _followingService.Get(cusInDb.Id, followeeId);

            if (following == null)
            {
                return BadRequest("You not follow this person yet"); //status code 400
            }
            //begin delete data
            try
            {
                _followingService.Delete(following.Id);
                _followingService.Save();
            }
            catch (Exception)
            {
                return BadRequest("Can not unfollow"); //stastus code 400
            }
            //end delete data
            return Ok("Success"); //status code 200
        }
    }
}
