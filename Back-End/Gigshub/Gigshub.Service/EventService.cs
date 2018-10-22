using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;

namespace Gigshub.Service
{
    public interface IEventService
    {
        Event GetByID(long Id);
        Event GetByName(string name);
        void Create(Event Event);
        void Save();
    }

    public class EventService : IEventService
    {
        #region Properties

        private readonly IEventRepository eventRepository;
        private readonly IUnitOfWork unitOfWork;

        #endregion

        public EventService(IEventRepository eventRepository,
            IUnitOfWork unitOfWork)
        {
            this.eventRepository = eventRepository;
            this.unitOfWork = unitOfWork;
        }

        #region IEventService Members

        public Event GetByID(long Id)
        {
            return eventRepository.GetById(Id);
        }

        public Event GetByName(string name)
        {
            return eventRepository.GetByName(name);
        }
        public void Create(Event Event)
        {
            eventRepository.Add(Event);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
