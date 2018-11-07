using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;

namespace Gigshub.Data.Repositories
{
    public class UserNotificationRepository : RepositoryBase<UserNotifcation>, IUserNotificationRepository
    {
        public UserNotificationRepository(IDbFactory dbFactory) : base(dbFactory) { }
    }

    public interface IUserNotificationRepository : IRepository<UserNotifcation>
    {

    }
}
