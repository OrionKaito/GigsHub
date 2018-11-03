using System.ComponentModel.DataAnnotations.Schema;

namespace Gigshub.Model.Model
{
    public class Attendance
    {
        public long Id { get; set; }
        public long CustomerId { get; set; }
        public long EventId { get; set; }

        [ForeignKey("CustomerId")]
        public virtual Customer Customer { get; set; }
        [ForeignKey("EventId")]
        public virtual Event Event { get; set; }
    }
}
