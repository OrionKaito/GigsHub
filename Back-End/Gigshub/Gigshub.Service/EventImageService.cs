using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;
using System.Collections.Generic;
using System.Linq;

namespace Gigshub.Service
{
    public interface IEventImageService
    {
        IEnumerable<string> GetAllByEventId(long eventId);
        string GetOneByEventId(long eventId);
        void Create(EventImage image);
        void DeleteByEventId(long Id);
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

        public IEnumerable<string> GetAllByEventId(long eventId)
        {
            return eventImageRepository
                .GetMany(k => k.EventId == eventId)
                .Select(k => k.ImagePath);
        }

        public string GetOneByEventId(long eventId)
        {
            return eventImageRepository
                .GetMany(k => k.EventId == eventId)
                .Select(k => k.ImagePath)
                .Single();
        }

        public void Create(EventImage image)
        {
            eventImageRepository.Add(image);
        }

        public void DeleteByEventId(long Id)
        {
            eventImageRepository.Delete(k => k.EventId == Id);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
