using Gigshub.Data.Configuration;
using Gigshub.Model.Model;
using Gigshub.Models;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;

namespace Gigshub.Data
{
    public class GigshubEntities : IdentityDbContext<ApplicationUser>
    {
        public GigshubEntities() : base("GigshubEntities")
        {
            Database.SetInitializer(new GigshubEntitiesSeed());
        }

        public static GigshubEntities Create()
        {
            return new GigshubEntities();
        }

        public DbSet<Customer> Customers { get; set; }

        public virtual void Commit()
        {
            base.SaveChanges();
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Conventions.Remove<PluralizingTableNameConvention>();
            modelBuilder.Configurations.Add(new CustomerConfiguration());
        }

        public class GigshubEntitiesSeed : DropCreateDatabaseIfModelChanges<GigshubEntities>
        {
            protected override void Seed(GigshubEntities context)
            {
                var roleStore = new RoleStore<IdentityRole>(context);
                var roleManager = new RoleManager<IdentityRole>(roleStore);
                var userStore = new UserStore<ApplicationUser>(context);
                var userManager = new UserManager<ApplicationUser>(userStore);

                //add roles
                var user = new IdentityRole("User");
                roleManager.Create(user);

                var admin = new IdentityRole("Admin");
                roleManager.Create(admin);

                //add user
                var PasswordHash = new PasswordHasher();
                var SeedUser = new ApplicationUser
                {
                    UserName = "admin",
                    Email = "admin@unknow.com",
                    EmailConfirmed = true,
                    PasswordHash = PasswordHash.HashPassword("123456"),
                    SecurityStamp = "1",
                };

                userManager.Create(SeedUser, SeedUser.PasswordHash);
                userManager.SetLockoutEnabled(SeedUser.Id, false);
                userManager.AddToRole(SeedUser.Id, "Admin");

                GetCustomer().ForEach(c => context.Customers.Add(c));
                base.Seed(context);
            }

            private static List<Customer> GetCustomer()
            {
                return new List<Customer>
                {
                    new Customer
                    {
                        UserName = "admin",
                        Email = "admin@unknow.com",
                        CreateDate = DateTime.Now,
                        DateOfBirth = DateTime.Now,
                    },
                };
            }
        }
    }
}
