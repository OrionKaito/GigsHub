using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Gigshub.ViewModel
{
    public class CustomerViewModel
    {
        public string UserName { get; set; }
    }

    public class CustomerCreateModel
    {
        public string Username { get; set; }
        public string Email { get; set; }
    }
}