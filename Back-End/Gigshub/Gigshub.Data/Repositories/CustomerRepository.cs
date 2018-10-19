using Gigshub.Data.Infrastructure;
using Gigshub.Model.Model;

namespace Gigshub.Data.Repositories
{
    public class CustomerRepository : RepositoryBase<Customer>, ICustomerRepository
    {
        public CustomerRepository(IDbFactory dbFactory) : base(dbFactory)
        {

        }
    }

    public interface ICustomerRepository : IRepository<Customer>
    {

    }
}
