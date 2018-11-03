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

        public Event GetByTitle(string title)
        {
            return this.DbContext.Events
                    .Where(k => k.Title == title)
                    .Where(k => k.IsDeleted != true)
                    .FirstOrDefault();
        }
    }

    public interface IEventRepository : IRepository<Event>
    {
        Event GetByTitle(string title);
    }
}
