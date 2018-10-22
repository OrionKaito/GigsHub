using System;
using System.Collections;

namespace Gigshub.Model.Model
{
    public class Customer
    {
        public long Id { get; set; }
        public string UserName { get; set; }
        public string Fullname { get; set; }
        public string Email { get; set; }
        public string Phonenumber { get; set; }
        public string Gender { get; set; }
        public string Address { get; set; }
        public DateTime? DateOfBirth { get; set; }
        public DateTime CreateDate { get; set; }
        public Boolean IsDeleted { get; set; }
    }
}
