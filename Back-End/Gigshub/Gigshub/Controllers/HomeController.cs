using Gigshub.Service;
using System.Web.Mvc;

namespace Gigshub.Controllers
{
    public class HomeController : Controller
    {
        private readonly ICustomerService _customerService;

        public HomeController(ICustomerService _customerService)
        {
            this._customerService = _customerService;
        }

        public ActionResult Index()
        {
            ViewBag.Title = "Home Page";
            _customerService.GetByID(1);
            return View();
        }
    }
}
