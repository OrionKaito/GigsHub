using System;
using System.Collections.Generic;

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
        public double AccountBalance { get; set; }
        public string EmailConfirmCode { get; set; }
        public string ImgPath { get; set; }
        public Boolean EmailConfirm { get; set; }
        public Boolean IsVerified { get; set; }
        public DateTime? DateOfBirth { get; set; }
        public DateTime CreateDate { get; set; }
        public Boolean IsDeleted { get; set; }

        public ICollection<Following> Followees { get; set; }
        public ICollection<Following> Followers { get; set; }
    }
}
