using System;
using System.Collections.Generic;

namespace Gigshub.ViewModel
{
    public class EventViewModel
    {
        public long Id { get; set; }
        public string Title { get; set; }
        public string Location { get; set; }
        public string Description { get; set; }
        public int NumberOfAttender { get; set; }
        public double Rating { get; set; }
        public Boolean IsDelete { get; set; }
        public Boolean IsSale { get; set; }
        public double Price { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }

        //conver to string
        public string OwnerName { get; set; }
        public string DateTime { get; set; }
        public string Category { get; set; }

        //image path
        //public IEnumerable<string> ImgPaths { get; set; }
        public string ImgPath { get; set; }
    }

    public class EventCreateModel
    {
        public string Name { get; set; }
        public string Title { get; set; }
        public string Location { get; set; }
        public string Description { get; set; }
        public DateTime DateTime { get; set; }
        public Boolean IsSale { get; set; }
        public double Price { get; set; }
        public long CategoryID { get; set; }
        public double Longitude { get; set; }
        public double Latitude { get; set; }
    }

    public class EventUpdateModel : EventCreateModel
    {
        public long Id { get; set; }
    }
}