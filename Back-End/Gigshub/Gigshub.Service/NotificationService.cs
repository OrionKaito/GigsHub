using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;

namespace Gigshub.Service
{
    public interface INotificationService
    {
        Notification GetById(long Id);
        void Create(Notification notification);
        void Save();
    }

    public class NotificationService : INotificationService
    {
        #region Properties

        private readonly INotificationRepository notificationRepository;
        private readonly IUnitOfWork unitOfWork;

        #endregion


        public NotificationService(INotificationRepository notificationRepository,
            IUnitOfWork unitOfWork)
        {
            this.notificationRepository = notificationRepository;
            this.unitOfWork = unitOfWork;
        }

        #region INotificationService Members

        public Notification GetById(long Id)
        {
            return notificationRepository.GetById(Id);
        }

        public void Create(Notification notification)
        {
            notificationRepository.Add(notification);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
