using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace Gigshub.Model.Model
{
    public class UserNotifcation
    {
        public long Id { get; set; }
        public Boolean IsRead { get; set; }

        public long CustomerId { get; set; }
        public long NotificationId { get; set; }

        [ForeignKey("CustomerId")]
        public Customer Customer { get; set; }
        [ForeignKey("NotificationId")]
        public Notification Notification { get; set; }
    }
}
