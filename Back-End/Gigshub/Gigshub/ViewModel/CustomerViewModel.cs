using System;
using System.Collections.Generic;

namespace Gigshub.ViewModel
{

    public class DataCustomerViewModel
    {
        public IEnumerable<CustomerViewModel> Data { get; set; }
    }

    public class CustomerViewModel : CustomerCreateModel
    {
        public long Id { get; set; }
        public string ImgPath { get; set; }
        public Boolean IsVerified { get; set; }
    }

    public class CustomerCreateModel
    {
        public string UserName { get; set; }
        public string Fullname { get; set; }
        public string Email { get; set; }
        public string Phonenumber { get; set; }
        public string Gender { get; set; }
        public string Address { get; set; }
        public DateTime? DateOfBirth { get; set; }
    }

    public class CustomerUpdateModel
    {
        public long Id { get; set; }
        public string Fullname { get; set; }
        //public string Phonenumber { get; set; }
        //public string Gender { get; set; }
        //public string Address { get; set; }
        //public DateTime DateOfBirth { get; set; }
    }
}