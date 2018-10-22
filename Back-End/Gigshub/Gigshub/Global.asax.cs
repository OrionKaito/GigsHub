using AutoMapper;
using Gigshub.App_Start;
using System.Web.Http;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using static Gigshub.Data.GigshubEntities;

namespace Gigshub
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            Mapper.Initialize(k => k.AddProfile<MappingProfile>());
            //Init database
            System.Data.Entity.Database.SetInitializer(new GigshubEntitiesSeed());

            AreaRegistration.RegisterAllAreas();
            GlobalConfiguration.Configure(WebApiConfig.Register);
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);

            //Autofac and Automapper configurations
            Bootstrapper.Run();
        }
    }
}
