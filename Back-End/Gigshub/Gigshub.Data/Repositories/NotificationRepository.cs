using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;

namespace Gigshub.Data.Repositories
{
    public class NotificationRepository : RepositoryBase<Notification>, INotificationRepository
    {
        public NotificationRepository(IDbFactory dbFactory) : base(dbFactory) { }
    }

    public interface INotificationRepository : IRepository<Notification>
    {
        
    }
}
