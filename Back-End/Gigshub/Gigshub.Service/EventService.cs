using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Gigshub.Service
{
    public interface IEventService
    {
        IEnumerable<Event> GetAll();
        IEnumerable<Event> GetAllUserEvent(long Id);
        IEnumerable<Event> SearchLikeTitle(string str);
        IEnumerable<Event> SearchLikeCity(string str);
        IEnumerable<Event> SearchByCategory(string str);
        Event GetByID(long Id);
        Event GetByTitle(string name);
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

        public IEnumerable<Event> GetAll()
        {
            return eventRepository
                .GetMany(k => k.IsDeleted == false)
                .Where(k => k.DateTime > DateTime.Now)
                .OrderBy(k => k.DateTime);
        }

        public IEnumerable<Event> GetAllUserEvent(long Id)
        {
            return eventRepository
                .GetMany(k => k.OwnerID == Id)
                .OrderBy(k => k.DateTime);
        }

        public IEnumerable<Event> SearchLikeTitle(string str)
        {
            return eventRepository
                .GetMany(k => k.Title.Contains(str))
                .Where(k => k.DateTime > DateTime.Now)
                .OrderBy(k => k.DateTime);
        }

        public IEnumerable<Event> SearchLikeCity(string str)
        {
            return eventRepository
                .GetMany(k => k.City.Contains(str))
                .Where(k => k.DateTime > DateTime.Now)
                .OrderBy(k => k.DateTime);
        }
        public IEnumerable<Event> SearchByCategory(string str)
        {
            return eventRepository
                .GetMany(k => k.Category.Name == str)
                .Where(k => k.DateTime > DateTime.Now)
                .OrderBy(k => k.DateTime);
        }

        public Event GetByID(long Id)
        {
            return eventRepository.GetById(Id);
        }

        public Event GetByTitle(string title)
        {
            return eventRepository.GetByTitle(title);
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
