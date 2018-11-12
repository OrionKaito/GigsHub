using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace Gigshub.Model.Model
{
    public class Comment
    {
        public long Id { get; set; }
        public long CustomerId { get; set; }
        public long EventId { get; set; }
        public string Content { get; set; }

        [ForeignKey("CustomerId")]
        public virtual Customer Customer { get; set; }
        [ForeignKey("EventId")]
        public virtual Event Event { get; set; }
    }
}
