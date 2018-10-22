using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;
using System.Linq;

namespace Gigshub.Data.Repositories
{
    public class CustomerRepository : RepositoryBase<Customer>, ICustomerRepository
    {
        public CustomerRepository(IDbFactory dbFactory) : base(dbFactory)
        {

        }

        public Customer GetCustomerByName(string name)
        {
            return this.DbContext.Customers
                .Where(c => c.UserName == name)
                .Where(c => c.IsDeleted != true)
                .FirstOrDefault();
        }
    }

    public interface ICustomerRepository : IRepository<Customer>
    {
        Customer GetCustomerByName(string name);
    }
}
