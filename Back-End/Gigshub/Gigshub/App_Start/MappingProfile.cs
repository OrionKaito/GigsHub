using AutoMapper;
using Gigshub.Model.Model;
using Gigshub.ViewModel;

namespace Gigshub.App_Start
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            Mapper.CreateMap<Customer, CustomerViewModel>();
            Mapper.CreateMap<CustomerViewModel, Customer>();
        }
    }
}