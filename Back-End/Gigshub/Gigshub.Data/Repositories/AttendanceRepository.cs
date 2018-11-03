using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;
using System.Linq;

namespace Gigshub.Data.Repositories
{
    public class AttendanceRepository : RepositoryBase<Attendance>, IAttendanceRepository
    {
        public AttendanceRepository(IDbFactory dbFactory) : base(dbFactory) { }

        public Attendance Get(long customerId, long eventId)
        {
            return this.DbContext.Attendances.Where(k => k.CustomerId == customerId && k.EventId == eventId).SingleOrDefault();
        }
    }

    public interface IAttendanceRepository : IRepository<Attendance>
    {
        Attendance Get(long customerId, long eventId);
    }
}
