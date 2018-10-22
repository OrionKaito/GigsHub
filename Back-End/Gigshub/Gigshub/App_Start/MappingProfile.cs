using AutoMapper;
using Gigshub.Model.Model;
using Gigshub.Models;
using Gigshub.ViewModel;

namespace Gigshub.App_Start
{
    public class MappingProfile : Profile
    {
        public MappingProfile()
        {
            Mapper.CreateMap<Customer, CustomerViewModel>();
            Mapper.CreateMap<CustomerViewModel, Customer>();

            Mapper.CreateMap<Customer, CustomerCreateModel>();
            Mapper.CreateMap<CustomerCreateModel, Customer>();

            Mapper.CreateMap<Customer, CustomerUpdateModel>();
            Mapper.CreateMap<CustomerUpdateModel, Customer>();

            Mapper.CreateMap<RegisterBindingModel, Customer>();
            Mapper.CreateMap<Customer,RegisterBindingModel>();

            Mapper.CreateMap<Event, EventViewModel>();
            Mapper.CreateMap<EventViewModel, Event>();

            Mapper.CreateMap<Event, EventCreateModel>();
            Mapper.CreateMap<EventCreateModel, Event>();

            Mapper.CreateMap<Event, EventUpdateModel>();
            Mapper.CreateMap<EventUpdateModel, Event>();
        }
    }
}