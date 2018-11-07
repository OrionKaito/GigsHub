using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;
using System.Linq;

namespace Gigshub.Data.Repositories
{
    public class EventCategoryRepository : RepositoryBase<EventCategory>, IEventCategoryRepository
    {
        public EventCategoryRepository(IDbFactory dbFactory) : base(dbFactory) { }

        public EventCategory GetByName(string name)
        {
            return this.DbContext.EventCategories
                .Where(k => k.Name == name)
                .FirstOrDefault();
        }
    }

    public interface IEventCategoryRepository : IRepository<EventCategory>
    {
        EventCategory GetByName(string name); 
    }
}
