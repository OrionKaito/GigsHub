using Gigshub.Model.Model;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.Infrastructure.Annotations;
using System.Data.Entity.ModelConfiguration;

namespace Gigshub.Data.Configuration
{
    public class EventCategoryConfiguration : EntityTypeConfiguration<EventCategory>
    {
        public EventCategoryConfiguration()
        {
            ToTable("EventCategorys");
            Property(k => k.Id).HasDatabaseGeneratedOption(DatabaseGeneratedOption.Identity);
            Property(k => k.Name).IsRequired()
                .HasColumnAnnotation(IndexAnnotation.AnnotationName, new IndexAnnotation(new IndexAttribute() { IsUnique = true }))
                .HasMaxLength(50);
        }
    }
}
