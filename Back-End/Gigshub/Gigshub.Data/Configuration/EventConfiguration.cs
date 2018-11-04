using Gigshub.Model.Model;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.Infrastructure.Annotations;
using System.Data.Entity.ModelConfiguration;

namespace Gigshub.Data.Configuration
{
    public class EventConfiguration : EntityTypeConfiguration<Event>
    {
        public EventConfiguration()
        {
            ToTable("Events");
            Property(k => k.Id).HasDatabaseGeneratedOption(DatabaseGeneratedOption.Identity);
            Property(k => k.Title).IsRequired()
                .HasColumnAnnotation(IndexAnnotation.AnnotationName, new IndexAnnotation(new IndexAttribute() { IsUnique = true}))
                .HasMaxLength(50);
            Property(k => k.DateTime).IsRequired();
            Property(k => k.OwnerID).IsRequired();
        }
    }
}
