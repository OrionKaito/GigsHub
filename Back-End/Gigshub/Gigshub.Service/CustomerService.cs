using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;

namespace Gigshub.Service
{
    public interface ICustomerService
    {
        Customer GetByID(long Id);
        Customer GetByName(string name);
        void Create(Customer customer);
        void Save();
    }

    public class CustomerService : ICustomerService
    {
        #region Properties

        private readonly ICustomerRepository customerRepository;
        private readonly IUnitOfWork unitOfWork;

        #endregion

        public CustomerService(ICustomerRepository customerRepository,
            IUnitOfWork unitOfWork)
        {
            this.customerRepository = customerRepository;
            this.unitOfWork = unitOfWork;
        }

        #region ICustomerService Members

        public Customer GetByID(long Id)
        {
            return customerRepository.GetById(Id);
        }

        public Customer GetByName(string name)
        {
            return customerRepository.GetCustomerByName(name);
        }

        public void Create(Customer customer)
        {
            customerRepository.Add(customer);
        }

        public void Save()
        {
            unitOfWork.Commit();
        }

        #endregion
    }
}
