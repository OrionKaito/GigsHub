using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;
using System.Data.Entity.Core.Metadata.Edm;

namespace Gigshub.Data.Repositories
{
    public class EventImageRepository : RepositoryBase<EventImage>, IEventImageRepsitory
    {
        public EventImageRepository(IDbFactory dbFactory) : base(dbFactory)
        {

        }
    }

    public interface IEventImageRepsitory : IRepository<EventImage>
    {

    }
}
