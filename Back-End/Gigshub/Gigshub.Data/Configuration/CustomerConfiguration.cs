using Gigshub.Model.Model;
using System.Data.Entity.ModelConfiguration;

namespace Gigshub.Data.Configuration
{
    public class CustomerConfiguration : EntityTypeConfiguration<Customer>
    {
        public CustomerConfiguration ()
        {
            ToTable("Customers");
            Property(c => c.Id).HasDatabaseGeneratedOption(System.ComponentModel.DataAnnotations.Schema.DatabaseGeneratedOption.Identity);
            Property(c => c.UserName).IsRequired().HasMaxLength(50);
            Property(c => c.Fullname).HasMaxLength(50);
            Property(c => c.Email).HasMaxLength(80);
            Property(c => c.Phonenumber).HasMaxLength(20);
            Property(c => c.Gender).HasMaxLength(10);
            Property(c => c.Address).HasMaxLength(50);
        }
    }
}
