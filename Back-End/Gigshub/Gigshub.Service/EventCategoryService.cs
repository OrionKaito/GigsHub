using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;
using System.Collections.Generic;
using System.Linq;

namespace Gigshub.Service
{
    public interface IEventCategoryService
    {
        IEnumerable<EventCategory> GetAll();
        EventCategory GetById(long Id);
        EventCategory GetByName(string name);
        void Create(EventCategory category);
        void Save();
    }

    public class EventCategoryService : IEventCategoryService
    {
        #region Properties

        private readonly IEventCategoryRepository eventCategoryRepository;
        private readonly IUnitOfWork unitOfWork;

        #endregion

        public EventCategoryService(IEventCategoryRepository eventCategoryRepository,
            IUnitOfWork unitOfWork)
        {
            this.eventCategoryRepository = eventCategoryRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IEventCategoryService Members

        public IEnumerable<EventCategory> GetAll()
        {
            return eventCategoryRepository.GetAll()
                .Where(k => k.IsDelete == false);
        }

        public EventCategory GetById(long Id)
        {
            return eventCategoryRepository.GetById(Id);
        }

        public EventCategory GetByName(string name)
        {
            return eventCategoryRepository.GetByName(name);
        }

        public void Create(EventCategory category)
        {
            eventCategoryRepository.Add(category);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
