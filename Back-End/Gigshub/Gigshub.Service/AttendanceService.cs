using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;

namespace Gigshub.Service
{
    public interface IAttendanceService
    {
        void Create(Attendance attendance);
        void Delete(long Id);
        Attendance Get(long customerId, long eventId);
        void Save();
    }

    public class AttendanceService : IAttendanceService
    {
        #region Properties

        private readonly IAttendanceRepository attendanceRepository;
        private readonly IUnitOfWork unitOfWork;

        #endregion

        public AttendanceService(IAttendanceRepository attendanceRepository,
            IUnitOfWork unitOfWork)
        {
            this.attendanceRepository = attendanceRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IAttendanceSerivce Members

        public void Create(Attendance attendance)
        {
            attendanceRepository.Add(attendance);
        }

        public void Delete(long Id)
        {
            var data = attendanceRepository.GetById(Id);
            attendanceRepository.Delete(data);
        }

        public Attendance Get(long customerId, long eventId)
        {
            return attendanceRepository.Get(customerId, eventId);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
