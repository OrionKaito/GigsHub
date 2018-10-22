using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;
using System.Linq;

namespace Gigshub.Data.Repositories
{
    public class EventRepository : RepositoryBase<Event>, IEventRepository
    {
        public EventRepository(IDbFactory dbFactory) : base(dbFactory)
        {
            
        }

        public Event GetByName(string name)
        {
            return this.DbContext.Events
                    .Where(k => k.Name == name)
                    .Where(k => k.IsDelete != true)
                    .FirstOrDefault();
        }
    }

    public interface IEventRepository : IRepository<Event>
    {
        Event GetByName(string name);
    }
}
