using System;

namespace Gigshub.ViewModel
{
    public class EventViewModel : EventCreateModel
    {
        public long Id { get; set; }
        public string OwnerName { get; set; }
        public int NumberOfAttender { get; set; }
        public float Rate { get; set; }
    }

    public class EventCreateModel
    {
        public string Name { get; set; }
        public string Title { get; set; }
        public string Location { get; set; }
        public string Description { get; set; }
        public DateTime DateTime { get; set; }
    }

    public class EventUpdateModel : EventCreateModel
    {
        public long Id { get; set; }
    }
}