using System.ComponentModel.DataAnnotations.Schema;

namespace Gigshub.Model.Model
{
    public class EventImage
    {
        public long Id { get; set; }
        public string ImagePath { get; set; }

        public long EventId { get; set; }
        [ForeignKey("EventId")]
        public virtual Event Event { get; set; }
    }
}
