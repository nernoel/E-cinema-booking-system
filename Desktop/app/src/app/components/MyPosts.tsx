import { PrismaClient } from "@prisma/client";
import { auth } from "@/app/utils/auth";
import StudySessionCard from "../components/StudySessionCard";


export default async function MyPosts(){
    const prisma = new PrismaClient();
    const session = await auth();
    
    const myPosts = await prisma.post.findMany({
        where: {
            userEmail: session?.user?.email! // email for now but change to name
        }
    });
    return (
        
        <div>
            <h1>MY STUDY POSTS</h1>
            <div className="flex">
                {myPosts.map(post => (
                    <StudySessionCard
                        key={post.id} // don't forget to add a unique key
                        title={post.title}
                        description={post.description}
                        userEmail={post.userEmail} 
                        location={""} isActive={false} 
                        isPrivate={false} 
                        createdAt={""}                    />
                ))}
                <button>Create new post</button>
            </div>
        </div>
    )
}