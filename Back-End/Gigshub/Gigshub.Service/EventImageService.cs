using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;

namespace Gigshub.Service
{
    public interface IEventImageService
    {
        void Create(EventImage image);
        void Save();
    }
    public class EventImageService : IEventImageService
    {
        #region Properties

        private readonly IEventImageRepsitory eventImageRepository;
        private readonly IUnitOfWork unitOfWork;

        #endregion

        public EventImageService (IEventImageRepsitory eventImageRepository,
            IUnitOfWork unitOfWork)
        {
            this.eventImageRepository = eventImageRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IEventImageSerivce Members

        public void Create(EventImage image)
        {
            eventImageRepository.Add(image);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
