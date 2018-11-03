using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;
using System.Linq;

namespace Gigshub.Data.Repositories
{
    public class FollowingRepository : RepositoryBase<Following>, IFollowingRepository
    {
        public FollowingRepository(IDbFactory dbFactory) : base(dbFactory){ }

        public Following Get(long followerId, long followeeId)
        {
            return this.DbContext.Followings.Where(k => k.FollowerId == followerId && k.FolloweeId == followeeId).SingleOrDefault();
        }
    }

    public interface IFollowingRepository : IRepository<Following>
    {
        Following Get(long followerId, long followeeId);
    }
}
