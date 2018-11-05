using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;
using System.Collections.Generic;
using System.Linq;

namespace Gigshub.Service
{
    public interface IAttendanceService
    {
        IEnumerable<Attendance> GetAll(long customerId);
        Attendance Get(long customerId, long eventId);
        void Create(Attendance attendance);
        void Delete(long Id);
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

        public IEnumerable<Attendance> GetAll(long customerId)
        {
            return attendanceRepository.GetMany(k => k.CustomerId == customerId);
        }

        public Attendance Get(long customerId, long eventId)
        {
            return attendanceRepository.Get(customerId, eventId);
        }

        public void Create(Attendance attendance)
        {
            attendanceRepository.Add(attendance);
        }

        public void Delete(long Id)
        {
            var data = attendanceRepository.GetById(Id);
            attendanceRepository.Delete(data);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
