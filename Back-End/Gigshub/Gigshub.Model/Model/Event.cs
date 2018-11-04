using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace Gigshub.Model.Model
{
    public class Event
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
        public DateTime DateTime { get; set; }
        public DateTime CreateDate { get; set; }
        public Boolean IsDeleted { get; set; }
        public Boolean IsSale { get; set; }

        public long OwnerID { get; set; }
        public long CategoryID { get; set; }
        [ForeignKey("OwnerID")]
        public virtual Customer Owner { get; set; }
        [ForeignKey("CategoryID")]
        public virtual EventCategory Category { get; set; }
    }
}
