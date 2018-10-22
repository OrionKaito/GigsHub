using Gigshub.Model.Model;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.Infrastructure.Annotations;
using System.Data.Entity.ModelConfiguration;

namespace Gigshub.Data.Configuration
{
    public class CustomerConfiguration : EntityTypeConfiguration<Customer>
    {
        public CustomerConfiguration ()
        {
            ToTable("Customers");
            Property(k => k.Id).HasDatabaseGeneratedOption(DatabaseGeneratedOption.Identity);
            Property(k=> k.UserName).IsRequired()
                .HasColumnAnnotation(IndexAnnotation.AnnotationName, new IndexAnnotation(new IndexAttribute() { IsUnique = true}))
                .HasMaxLength(50);
            Property(k=> k.Fullname).HasMaxLength(50);
            Property(k=> k.Email).IsRequired().HasMaxLength(50);
            Property(k=> k.Phonenumber).HasMaxLength(20);
            Property(k=> k.Gender).HasMaxLength(10);
            Property(k=> k.Address).HasMaxLength(50);
        }
    }
}
