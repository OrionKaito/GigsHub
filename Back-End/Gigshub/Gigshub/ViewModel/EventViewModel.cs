using System;
using System.Collections.Generic;

namespace Gigshub.ViewModel
{
    public class DataEventViewModel
    {
        public IEnumerable<EventViewModel> Data { get; set; }
    }

    public class EventViewModel
    {
        public long Id { get; set; }
        public string Title { get; set; }
        public string City { get; set; }
        public string Address { get; set; }
        public string Description { get; set; }
        public string Artist { get; set; }
        public int NumberOfAttender { get; set; }
        public double Rating { get; set; }
        public double Price { get; set; }
        public Boolean IsDeleted { get; set; }
        public Boolean IsSale { get; set; }

        //conver to string
        public string OwnerName { get; set; }
        public string Date { get; set; }
        public string Time { get; set; }
        public string Category { get; set; }

        //image path
        //public IEnumerable<string> ImgPaths { get; set; }
        public string ImgPath { get; set; }
    }

    public class EventCreateModel
    {
        public string Title { get; set; }
        public string City { get; set; }
        public string Address { get; set; }
        public string Description { get; set; }
        public string Artist { get; set; }
        public double Price { get; set; }
        public DateTime DateTime { get; set; }
        public Boolean IsDeleted { get; set; }
        public Boolean IsSale { get; set; }
        public long CategoryID { get; set; }
    }

    public class EventUpdateModel
    {
        public long Id { get; set; }
        public string City { get; set; }
        public string Address { get; set; }
        public string Description { get; set; }
        public string Artist { get; set; }
        public double Price { get; set; }
        public DateTime DateTime { get; set; }
        public Boolean IsDeleted { get; set; }
        public Boolean IsSale { get; set; }
        public long CategoryID { get; set; }
    }
}