using System;

namespace Gigshub.ViewModel
{
    public class CustomerViewModel : CustomerCreateModel
    {
        public long Id { get; set; }
        public string ImgPath { get; set; }
    }

    public class CustomerCreateModel
    {
        public string UserName { get; set; }
        public string Fullname { get; set; }
        public string Email { get; set; }
        public string Phonenumber { get; set; }
        public string Gender { get; set; }
        public string Address { get; set; }
        public DateTime DateOfBirth { get; set; }
    }

    public class CustomerUpdateModel
    {
        public long Id { get; set; }
        public string Fullname { get; set; }
        public string Phonenumber { get; set; }
        public string Gender { get; set; }
        public string Address { get; set; }
        public DateTime DateOfBirth { get; set; }
    }
}