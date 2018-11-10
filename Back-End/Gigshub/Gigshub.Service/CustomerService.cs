using Gigshub.Data.Infrastructure;
using Gigshub.Data.Repositories;
using Gigshub.Model.Model;
using System.Collections.Generic;
using System.Linq;

namespace Gigshub.Service
{
    public interface ICustomerService
    {
        IEnumerable<Customer> SearchLikeFullName(string fullname);
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

        public IEnumerable<Customer> SearchLikeFullName(string fullname)
        {
            return customerRepository
                .GetMany(k => k.Fullname.Contains(fullname))
                .Where(k => k.IsDeleted == false);
        }

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
