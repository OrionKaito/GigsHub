using System;
using System.ComponentModel.DataAnnotations;

namespace Gigshub.Model.Model
{
    public class Notification
    {
        public long Id { get; set; }
        public DateTime DateTime { get; set; }
        public NotificationType Type { get; set; }
        //public DateTime? OriginalDateTime { get; set; }
        //public string OriginalValue { get; set; }

        public Event Event { get; set; }
    }
}
