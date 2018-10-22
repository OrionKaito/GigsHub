using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace Gigshub.Model.Model
{
    public class Event
    {
        public long Id { get; set; }
        public string Name { get; set; }
        public string Title { get; set; }
        public string Location { get; set; }
        public string Description { get; set; }
        public int NumberOfAttender { get; set; }
        public float Rate { get; set; }
        public DateTime DateTime { get; set; }
        public DateTime CreateDate { get; set; }
        public Boolean IsDelete { get; set; }

        public long OwnerID { get; set; }
        [ForeignKey("OwnerID")]
        public virtual Customer Customer { get; set; }
    }
}
