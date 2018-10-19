using Autofac;
using Autofac.Integration.Mvc;
using Autofac.Integration.WebApi;
using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Service;
using System.Linq;
using System.Reflection;
using System.Web.Http;
using System.Web.Mvc;

namespace Gigshub.App_Start
{
    public class Bootstrapper
    {
        public static void Run()
        {
            SetAutoFacContainer();
        }

        public static void SetAutoFacContainer()
        {
            var builder = new ContainerBuilder();
            builder.RegisterControllers(Assembly.GetExecutingAssembly());
            builder.RegisterType<UnitOfWork>().As<IUnitOfWork>().InstancePerRequest();
            builder.RegisterType<DbFactory>().As<IDbFactory>().InstancePerRequest();

            //Repositories
            builder.RegisterAssemblyTypes(typeof(CustomerRepository).Assembly)
                .Where(t => t.Name.EndsWith("Repository"))
                .AsImplementedInterfaces().InstancePerRequest(); ;
            //Service
            builder.RegisterAssemblyTypes(typeof(CustomerService).Assembly)
                .Where(t => t.Name.EndsWith("Service"))
                .AsImplementedInterfaces().InstancePerRequest();

            IContainer container = builder.Build();
            DependencyResolver.SetResolver(new AutofacDependencyResolver(container));
            GlobalConfiguration.Configuration.DependencyResolver = new AutofacWebApiDependencyResolver(container);
        }
    }
}